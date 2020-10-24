/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi6.manchestersyntax.renderer;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.model.PrefixManager;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class AbstractRenderer {

    private final Writer writer;
    private final List<Integer> tabs = new ArrayList<>();
    protected PrefixManager prefixManager;
    private int lastNewLinePos = -1;
    private int currentPos;
    private boolean useTabbing = true;
    private boolean useWrapping = true;

    /**
     * @param writer writer
     * @param shortFormProvider shortFormProvider
     */
    protected AbstractRenderer(Writer writer, PrefixManager shortFormProvider) {
        this.writer = writer;
        prefixManager = shortFormProvider;
        pushTab(0);
    }

    /**
     * @return true if output should be wrapped
     */
    protected boolean isUseWrapping() {
        return useWrapping;
    }

    /**
     * @param useWrapping true if output should be wrapped
     */
    protected void setUseWrapping(boolean useWrapping) {
        this.useWrapping = useWrapping;
    }

    /**
     * @return true if tabs should be used
     */
    protected boolean isUseTabbing() {
        return useTabbing;
    }

    /**
     * @param useTabbing true if tabs should be used
     */
    protected void setUseTabbing(boolean useTabbing) {
        this.useTabbing = useTabbing;
    }

    /**
     * Flush.
     *
     * @throws OWLOntologyStorageException renderer error
     */
    protected void flush() throws OWLOntologyStorageException {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    protected void pushTab(int size) {
        tabs.add(0, Integer.valueOf(size));
    }

    protected void incrementTab(int increment) {
        int base = 0;
        if (!tabs.isEmpty()) {
            base = tabs.get(0).intValue();
        }
        tabs.add(0, Integer.valueOf(base + increment));
    }

    protected void popTab() {
        tabs.remove(0);
    }

    protected AbstractRenderer writeTab() {
        int tab = tabs.get(0).intValue();
        char[] c = new char[tab];
        Arrays.fill(c, ' ');
        write(new String(c));
        return this;
    }

    protected int getIndent() {
        return currentPos - lastNewLinePos - 2;
    }

    protected AbstractRenderer write(@Nullable String s) {
        if (s == null) {
            return this;
        }
        int indexOfNewLine = s.indexOf('\n');
        if (indexOfNewLine != -1) {
            lastNewLinePos = currentPos + indexOfNewLine;
        }
        currentPos += s.length();
        try {
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
        return this;
    }

    protected AbstractRenderer writeSpace() {
        return write(" ");
    }

    protected AbstractRenderer write(ManchesterOWLSyntax keyword) {
        return write(" ", keyword, " ");
    }

    protected AbstractRenderer writeFrameKeyword(ManchesterOWLSyntax keyword) {
        return write("", keyword, ": ");
    }

    protected AbstractRenderer writeSectionKeyword(ManchesterOWLSyntax keyword) {
        return write(" ", keyword, ": ");
    }

    protected AbstractRenderer writeNewLine() {
        write("\n");
        if (useTabbing) {
            return writeTab();
        }
        return this;
    }

    protected AbstractRenderer write(String prefix, ManchesterOWLSyntax keyword, String suffix) {
        return write(prefix).write(keyword.toString()).write(suffix);
    }

    protected PrefixManager getPrefixManager() {
        return prefixManager;
    }

    protected void setShortFormProvider(PrefixManager p) {
        prefixManager = p;
    }

    protected AbstractRenderer writeLiteral(String literal) {
        return write("\"").write(literal.replace("\\", "\\\\").replace("\"", "\\\"")).write("\"");
    }
}