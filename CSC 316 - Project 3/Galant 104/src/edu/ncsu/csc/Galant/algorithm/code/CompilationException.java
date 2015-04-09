package edu.ncsu.csc.Galant.algorithm.code;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

/** 
 * Indicates that compiling some code resulted in one or more compiler errors.
 * @author Jason Cockrell, Ty Devries, Alex McCabe, Michael Owoc
 */
public class CompilationException extends Exception
	{
		private static final long serialVersionUID = 1L;

		private DiagnosticCollector<JavaFileObject> diagnostics;

		public CompilationException(DiagnosticCollector<JavaFileObject> diagnostics)
			{
				super(diagnostics.getDiagnostics().toString());
				this.diagnostics = diagnostics;
			}

		/** Returns the <code>DiagnosticCollector</code> containing the details of the errors. */
		public DiagnosticCollector<JavaFileObject> getDiagnostics()
			{
				return diagnostics;
			}
	}
