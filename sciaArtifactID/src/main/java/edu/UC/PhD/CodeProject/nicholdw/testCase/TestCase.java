package edu.UC.PhD.CodeProject.nicholdw.testCase;

public abstract class TestCase implements ITestCase {
	private String title;
	
	public TestCase(String title) throws TestCaseException {
		setTitle(title);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws TestCaseException {
		if (title.trim().length() > 0) {
			this.title = title.trim();
		} else {
			throw new TestCaseException("TestCase.setTitla: title cannot be blank");
		}
	}
}
