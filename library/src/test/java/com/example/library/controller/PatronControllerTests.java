package com.example.library.controller;

import com.example.library.TestSecurityConfig;
import com.example.library.model.Book;
import com.example.library.model.Patron;
import com.example.library.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatronController.class)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class PatronControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    private Patron patron;

    @BeforeEach
    public void setUp() {
        patron = new Patron("John Doe", "john12","john@doe.com","pass");
    }

    @Test
    public void getAllPatrons() throws Exception {
        given(patronService.getAllPatrons()).willReturn(Arrays.asList(patron));

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(patron.getName()));
    }

    @Test
    public void getAllPatrons_EmptyList() throws Exception {
        given(patronService.getAllPatrons()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getPatronById() throws Exception {
        given(patronService.getPatronById(1L)).willReturn(patron);

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(patron.getName()));
    }

    @Test
    public void getPatronById_NotFound() throws Exception {
        given(patronService.getPatronById(1L)).willReturn(null);

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addPatron() throws Exception {
        Patron newPatron = new Patron("John Doe", "john12","john@doe.com","pass");

        given(patronService.addPatron(Mockito.any(Patron.class))).willReturn(newPatron);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\", \"email\": \"john@doe.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(newPatron.getName()));
    }

    @Test
    public void updatePatron() throws Exception {
        Patron updatedPatron = new Patron("John Doe", "john12","john@doe.com","pass");

        given(patronService.updatePatron(Mockito.anyLong(), Mockito.any(Patron.class))).willReturn(updatedPatron);

        mockMvc.perform(put("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\", \"email\": \"john@doe.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedPatron.getName()));
    }

    @Test
    public void updatePatron_NotFound() throws Exception {
        given(patronService.updatePatron(Mockito.anyLong(), Mockito.any(Patron.class))).willReturn(null);

        mockMvc.perform(put("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\", \"email\" : \"john@doe.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePatron() throws Exception {
        Mockito.doNothing().when(patronService).deletePatron(1L);

        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isNoContent());
    }


}
