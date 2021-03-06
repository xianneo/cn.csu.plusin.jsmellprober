package cn.csu.plusin.jsmellprober.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import cn.csu.plusin.jsmellprober.model.MethodParam;
import cn.csu.plusin.jsmellprober.util.FileUtil;
import cn.csu.plusin.jsmellprober.util.JdtAstUtil;

public class MethodCounter extends ASTVisitor {
	
	private String unitPath;
	private CompilationUnit unit;
	private HashMap<Integer, ArrayList<MethodParam>> methodCounter = new HashMap<Integer, ArrayList<MethodParam>>();
	private MethodParam param;
	private int count = 0;
	
	public MethodCounter(CompilationUnit unit)throws Exception {
		super();
		this.unit = unit ;
		
	}
	
	public void getResult() {
//		System.out.println("Threshold\tCount");
		Iterator iter = methodCounter.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Integer key = (Integer)entry.getKey();
			ArrayList<MethodParam> val = (ArrayList<MethodParam>) entry.getValue();
			
			System.out.print(
//					key + ": "+
					 
					val.size()+"\t" );

		}
		System.out.println();

//		System.out.println("Count of Method:" + count);
	}
	
	public MethodCounter(String dirPath) throws Exception {
		super();
		
		FileUtil util = new FileUtil();
		ArrayList<String> pathList = util.getAllJavaFilePath(dirPath);
		
		for (String path: pathList) {
			unitPath = path;
			unit = JdtAstUtil.getCompilationUnit(unitPath);
			unit.accept(this);
			
		}
		getResult();
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		count++;
		
		param = new MethodParam();
		param.setPath(unitPath);
		param.setMethodName(node.getName().toString());
		param.setStartLineNum(unit.getLineNumber(node.getStartPosition()));
		
		if (methodCounter.get(node.parameters().size()) == null) {
			methodCounter.put(node.parameters().size(), new ArrayList<MethodParam>());
		}
		methodCounter.get(node.parameters().size()).add(param);
		
//		return super.visit(node);
		return true;
	}
	
//	class MethodParam {
//		String path;
//		String methodName;
//		int lineNum;
//		
//		public String getPath() {
//			return path;
//		}
//		public void setPath(String path) {
//			this.path = path;
//		}
//		public String getMethodName() {
//			return methodName;
//		}
//		public void setMethodName(String methodName) {
//			this.methodName = methodName;
//		}
//		public int getLineNum() {
//			return lineNum;
//		}
//		public void setLineNum(int lineNum) {
//			this.lineNum = lineNum;
//		}
//		
//	}
}
