/**
 * Copyright (C) 2010 Marvin Herman Froeder (marvin@marvinformatics.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marvinformatics.formatter;

import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Copyright 2010. All work is copyrighted to their respective author(s),
 * unless otherwise stated.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Test class for {@link LineEnding}.
 * 
 * @author Matt Blanchette
 * @author marvin.froeder
 */
public class LineEndingTest {

	/**
	 * Test successfully determining CRLF line ending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void success_read_line_endings_crlf() throws Exception {
		String fileData = "Test\r\nTest\r\nTest\r\n";
		LineEnding lineEnd = LineEnding.determineLineEnding(fileData);
		assertEquals(LineEnding.CRLF, lineEnd);
	}

	/**
	 * Test successfully determining LF line ending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void success_read_line_endings_lf() throws Exception {
		String fileData = "Test\nTest\nTest\n";
		LineEnding lineEnd = LineEnding.determineLineEnding(fileData);
		assertEquals(LineEnding.LF, lineEnd);
	}

	/**
	 * Test successfully determining CR line ending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void success_read_line_endings_cr() throws Exception {
		String fileData = "Test\rTest\rTest\r";
		LineEnding lineEnd = LineEnding.determineLineEnding(fileData);
		assertEquals(LineEnding.CR, lineEnd);
	}

	/**
	 * Test successfully determining LF line ending with mixed endings.
	 * 
	 * @throws Exception
	 */
	@Test
	public void success_read_line_endings_mixed_lf() throws Exception {
		String fileData = "Test\r\nTest\rTest\nTest\nTest\r\nTest\n";
		LineEnding lineEnd = LineEnding.determineLineEnding(fileData);
		assertEquals(LineEnding.LF, lineEnd);
	}

	/**
	 * Test successfully determining AUTO line ending with mixed endings and no
	 * clear majority.
	 * 
	 * @throws Exception
	 */
	@Test
	public void success_read_line_endings_mixed_auto() throws Exception {
		String fileData = "Test\r\nTest\r\nTest\nTest\nTest\r\nTest\nTest\r";
		LineEnding lineEnd = LineEnding.determineLineEnding(fileData);
		assertEquals(LineEnding.UNKNOW, lineEnd);
	}

	/**
	 * Test successfully determining AUTO line ending with no endings.
	 * 
	 * @throws Exception
	 */
	@Test
	public void success_read_line_endings_none_auto() throws Exception {
		String fileData = "TestTestTestTest";
		LineEnding lineEnd = LineEnding.determineLineEnding(fileData);
		assertEquals(LineEnding.UNKNOW, lineEnd);
	}

	@Test
	public void keepLineEnding() throws Exception {
		String result = LineEnding.KEEP.fix("a\r\na\na\ra");
		assertEquals("a\r\na\na\ra", result);
	}

	@Test
	public void fixUnknowLineEnding() throws Exception {
		String result = LineEnding.LF.fix("a\r\na\na\ra");
		assertEquals("a\na\na\na", result);
	}

	@Test
	public void fixWindowsToLinux() throws Exception {
		String result = LineEnding.LF.fix("a\r\na\r\na");
		assertEquals("a\na\na", result);
	}

	@Test
	public void skipChange() throws Exception {
		String result = LineEnding.LF.fix("a\na\na");
		assertEquals("a\na\na", result);
		assertTrue("a\na\na" == result);
	}

}
