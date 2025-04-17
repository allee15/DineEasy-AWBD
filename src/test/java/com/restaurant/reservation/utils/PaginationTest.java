package com.restaurant.reservation.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaginationTest {

    @Test
    void testTotalPages() {
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        Pagination<Integer> pagination = new Pagination<>(data, 1, 3);
        assertEquals(3, pagination.getTotalPages());
    }

    @Test
    void testGetCurrentPageData_FirstPage() {
        List<String> data = Arrays.asList("A", "B", "C", "D", "E");
        Pagination<String> pagination = new Pagination<>(data, 1, 2);
        assertEquals(Arrays.asList("A", "B"), pagination.getCurrentPageData());
    }

    @Test
    void testGetCurrentPageData_LastPageWithLessItems() {
        List<String> data = Arrays.asList("A", "B", "C", "D", "E");
        Pagination<String> pagination = new Pagination<>(data, 3, 2);
        assertEquals(List.of("E"), pagination.getCurrentPageData());
    }

    @Test
    void testHasNextAndPreviousPage() {
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        Pagination<Integer> pagination = new Pagination<>(data, 2, 2);

        assertTrue(pagination.hasNextPage());
        assertTrue(pagination.hasPreviousPage());
    }

    @Test
    void testNextPageAndPreviousPage() {
        List<Integer> data = Arrays.asList(1, 2, 3, 4);
        Pagination<Integer> pagination = new Pagination<>(data, 1, 2);

        assertEquals(2, pagination.nextPage());
        assertEquals(1, pagination.previousPage());
    }

    @Test
    void testNoNextPage() {
        List<Integer> data = Arrays.asList(1, 2, 3);
        Pagination<Integer> pagination = new Pagination<>(data, 2, 2);

        assertFalse(pagination.hasNextPage());
        assertEquals(2, pagination.nextPage());
    }

    @Test
    void testNoPreviousPage() {
        List<Integer> data = Arrays.asList(1, 2, 3);
        Pagination<Integer> pagination = new Pagination<>(data, 1, 2);

        assertFalse(pagination.hasPreviousPage());
        assertEquals(1, pagination.previousPage());
    }
}