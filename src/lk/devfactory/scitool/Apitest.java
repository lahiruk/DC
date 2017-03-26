package lk.devfactory.scitool;

import com.scitools.understand.Database;
import com.scitools.understand.Entity;
import com.scitools.understand.Reference;
import com.scitools.understand.Understand;
import com.scitools.understand.UnderstandException;

public class Apitest {
	public static String projPath = "/Users/lahiru/Development/workspace/SciToolPOC/project.udb";

	public static void main(String[] args) {
		System.out.println("###############");
		unusedPrivateFunctions();
		System.out.println("@@@@@@@@@@@@@@@");
		unusedPrivateVariables();
		System.out.println("&&&&&&&&&&&&&&&");
		unusedParameters();
	}

	private static void unusedPrivateFunctions() {
		try {
			// Open the Understand Database
			Database db = Understand.open(projPath);
			// Get a list of all functions and methods
			Entity[] funcs = db.ents("function ~unknown ~unresolved,method ~unknown ~unresolved");
			for (Entity e : funcs) {
				System.out.println(e.name()+" : "+e.kind());
				
				// Get a list of all functions and methods has invocations
				Reference[] callRefs = e.refs("call",null,true);
				for (Reference pRef : callRefs) {
					System.out.println(e.name() + " => " + pRef.ent().name() +" ["+pRef.line()+":"+pRef.column()+"]");
				}
			}
		} catch (UnderstandException e) {
			System.out.println("Failed opening Database:" + e.getMessage());
			System.exit(0);
		}
	}
	 
	private static void unusedPrivateVariables() {
		try {
			// Open the Understand Database
			Database db = Understand.open(projPath);
			// Get a list of all variables
			Entity[] vars = db.ents("variable ~unknown");
			
			for (Entity e : vars) {
				System.out.println(e.name()+" : "+e.kind());
				
				// Get a list of all used variables
				Reference[] useByRefs = e.refs("Useby",null,true);
				for (Reference pRef : useByRefs) {
					System.out.println(e.name() + " => " + pRef.ent().name() +" ["+pRef.line()+":"+pRef.column()+"]");
				}
			}
		} catch (UnderstandException e) {
			System.out.println("Failed opening Database:" + e.getMessage());
			System.exit(0);
		}
	}
	
	private static void unusedParameters() {
		try {
			// Open the Understand Database
			Database db = Understand.open(projPath);
			// Get a list of all functions and methods
			Entity[] funcs = db.ents("function ~unknown ~unresolved,method ~unknown ~unresolved");
			
			for (Entity e : funcs) {
				System.out.println(e.name()+" : "+e.kind());
				
				// Get a list of all params of functions and methods
				Reference[] paramterRefs = e.refs("define", "parameter", true);
				for (Reference pRef : paramterRefs) {
					e = pRef.ent();
					System.out.println(e.name() + " => " + pRef.ent().name() +" ["+pRef.line()+":"+pRef.column()+"]");
					
					// Get a list of all used params
					Reference[] useByRefs = e.refs("Useby",null,true);
					for (Reference uRef : useByRefs) {
						System.out.println(e.name() + " =>* " + uRef.ent().name() +" ["+uRef.line()+":"+uRef.column()+"]");
					}
				}
			}
		} catch (UnderstandException e) {
			System.out.println("Failed opening Database:" + e.getMessage());
			System.exit(0);
		}
	}
}