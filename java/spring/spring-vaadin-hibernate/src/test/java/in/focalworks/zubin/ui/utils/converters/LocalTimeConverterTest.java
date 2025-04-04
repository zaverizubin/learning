/**
 *
 */
package in.focalworks.zubin.ui.utils.converters;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Test;

import in.focalworks.zubin.test.FormattingTest;

public class LocalTimeConverterTest extends FormattingTest {

	@Test
	public void formattingShoudBeLocaleIndependent() {
		LocalTimeConverter converter = new LocalTimeConverter();
		String result = converter.encode(LocalTime.of(13, 9, 33));
		assertEquals("1:09 PM", result);
	}
}
