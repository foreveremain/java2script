/*******************************************************************************
 * Copyright (c) 2007 java2script.org and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zhou Renjian - initial API and implementation
 *******************************************************************************/
package net.sf.j2s.core.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.compiler.CategorizedProblem;


/**
 * @author zhou renjian
 *
 */
public class CompilationParticipantResultProxy {
	
	private CompilationParticipantResult context;
	
	public CompilationParticipantResultProxy(CompilationParticipantResult context) {
		this.context = context;
	}

	public SourceFile getSourceFile() {
		return context.sourceFile;
	}
	
	public boolean getHasAnnotations() {
		return context.hasAnnotations; // only set during processAnnotations
	}
	
	public IFile[] getAddedFiles() {
		return context.addedFiles; // added/changed generated source files that need to be compiled
	}
	
	public IFile[] getDeletedFiles() {
		return context.deletedFiles; // previously generated source files that should be deleted
	}
	
	public CategorizedProblem[] getProblems() {
		return context.problems; // new problems to report against this compilationUnit
	}
	
	public String[] getDependencies() {
		return context.dependencies; // fully-qualified type names of any new dependencies, each name is of the form 'p1.p2.A.B'
	}

	public void setHasAnnotations(boolean hasAnnotation) {
		context.hasAnnotations = hasAnnotation; // only set during processAnnotations
	}
	
	public void setAddedFiles(IFile[] addedFiles) {
		context.addedFiles = addedFiles; // added/changed generated source files that need to be compiled
	}
	
	public void setDeletedFiles(IFile[] deletedFiles) {
		context.deletedFiles = deletedFiles; // previously generated source files that should be deleted
	}
	
	public void setProblems(CategorizedProblem[] problems) {
		context.problems = problems; // new problems to report against this compilationUnit
	}
	
	public void setDependencies(String[] dependencies) {
		context.dependencies = dependencies; // fully-qualified type names of any new dependencies, each name is of the form 'p1.p2.A.B'
	}

}
