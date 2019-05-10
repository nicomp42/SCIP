package edu.UC.PhD.CodeProject.nicholdw.testCase;

public abstract class TestCase implements ITestCase {
	
	public static final String root = "TestCases";		// src/main/resources/{root} for example
	private String title;
	private String filePath;
	
	public TestCase(String title, String filePath) throws TestCaseException {
		setTitle(title);
		setFilePath(filePath);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws TestCaseException {
		if (title.trim().length() > 0) {
			this.title = title.trim();
		} else {
			throw new TestCaseException("TestCase.setTitle: title cannot be blank");
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) throws TestCaseException {
		if (filePath.trim().length() > 0) {
			this.filePath = filePath;
		} else {
			throw new TestCaseException("TestCase.setFilePath: file path cannot be blank");
		}
	}
}
