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

/**
 * @author marvin.froeder
 */
public enum LineEnding {

	/**
	 * Use java default line separator for current OS
	 */
	AUTO(System.getProperty("line.separator")),

	/**
	 * Don't change line endings
	 */
	KEEP(null),

	/**
	 * Linux line endings
	 */
	LF("\n"),

	/**
	 * Windows line endings
	 */
	CRLF("\r\n"),

	/**
	 * OSx line endings
	 */
	CR("\r"),

	/**
	 * Unable to determine line ending
	 */
	UNKNOW(null);

	private final String chars;

	LineEnding(String chars) {
		this.chars = chars;
	}

	public String getChars() {
		return chars;
	}

	/**
	 * Returns the most occurring line-ending characters in the file text or
	 * null if no line-ending occurs the most.
	 * 
	 * @param fileDataString
	 * @return
	 */
	public static LineEnding determineLineEnding(String fileDataString) {
		int lfCount = 0;
		int crCount = 0;
		int crlfCount = 0;

		for (int i = 0; i < fileDataString.length(); i++) {
			char c = fileDataString.charAt(i);
			if (c == '\r') {
				if ((i + 1) < fileDataString.length() && fileDataString.charAt(i + 1) == '\n') {
					crlfCount++;
					i++;
				} else {
					crCount++;
				}
			} else if (c == '\n') {
				lfCount++;
			}
		}

		if (lfCount > crCount && lfCount > crlfCount) {
			return LF;
		} else if (crlfCount > lfCount && crlfCount > crCount) {
			return CRLF;
		} else if (crCount > lfCount && crCount > crlfCount) {
			return CR;
		}
		return UNKNOW;
	}

	/**
	 * Apply line ending to text
	 * 
	 * @param text
	 * @return
	 */
	public String fix(String text) {
		if (this == LineEnding.KEEP)
			return text;

		LineEnding current = LineEnding.determineLineEnding(text);
		if (current == LineEnding.UNKNOW)
			return text.replaceAll("(\\r\\n)|(\\n)|(\\r)", this.getChars());

		if (this == current)
			return text;

		return text.replace(current.getChars(), this.getChars());
	}

}
