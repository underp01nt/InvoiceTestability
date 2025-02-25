package org.example.Tutorial1;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ListTest {

	@Test
	public void letsMockListSize() {
		List list = mock(List.class);
		when(list.size()).thenReturn(10);
		assertEquals(10, list.size());
	}

	@Test
	public void letsMockListSizeWithMultipleReturnValues() {
		List list = mock(List.class);

		// This test fails because list.size() is supposed to return a specific value but we have not mocked it yet
		// TODO: Make this test pass using "stub".
		when(list.size()).thenReturn(10, 20);

		assertEquals(10, list.size()); // First Call
		assertEquals(20, list.size()); // Second Call
	}

	@Test
	public void letsMockListGet() {
		List<String> list = mock(List.class);
		when(list.get(0)).thenReturn("0th element");
		assertEquals("0th element", list.get(0));
		// Does the next line pass or fail? Why? If it fails, how do we make it pass? What is the value of list.get(1)?
		assertNull(list.get(1));
	}

	@Test
	public void letsMockListGetWithAny() {
		List<String> list = mock(List.class);
		when(list.get(Mockito.anyInt())).thenReturn("this is an int");
		assertEquals("this is an int", list.get(0));
		assertEquals("this is an int", list.get(1));
	}
}