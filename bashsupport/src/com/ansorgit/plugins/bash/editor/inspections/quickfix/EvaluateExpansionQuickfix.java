/*
 * Copyright 2009 Joachim Ansorg, mail@ansorg-it.com
 * File: EvaluateExpansionQuickfix.java, Class: EvaluateExpansionQuickfix
 * Last modified: 2010-01-27
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ansorgit.plugins.bash.editor.inspections.quickfix;

import com.ansorgit.plugins.bash.lang.psi.api.word.BashExpansion;
import com.ansorgit.plugins.bash.lang.valueExpansion.ValueExpansionUtil;
import com.ansorgit.plugins.bash.settings.BashProjectSettings;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * User: jansorg
 * Date: Nov 15, 2009
 * Time: 12:50:35 AM
 */
public class EvaluateExpansionQuickfix extends AbstractBashQuickfix {
    private final BashExpansion expansion;

    public EvaluateExpansionQuickfix(BashExpansion expansion) {
        this.expansion = expansion;
    }

    @NotNull
    public String getName() {
        return "Replace with evaluated expansion";
    }

    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        TextRange r = expansion.getTextRange();

        boolean supportBash4 = BashProjectSettings.storedSettings(project).isSupportBash4();
        String replacement = ValueExpansionUtil.expand(expansion.getText(), supportBash4);
        if (replacement != null) {
            editor.getDocument().replaceString(r.getStartOffset(), r.getEndOffset(), replacement);
        }
    }
}