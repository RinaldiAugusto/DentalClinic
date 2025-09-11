package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IDentistService;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Dentist;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DentistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IDentistService dentistService;

    public void dataLoad(){
        Dentist dentist = new Dentist();
        dentist.setRegistration(401036);
        dentist.setName("Augusto");
        dentist.setLastName("Rinaldi");
        dentistService.save(dentist);
    }

    @Test
    @Order(2)
    public void testGetDentistById() throws Exception {
        dataLoad();
        mockMvc.perform(get("/dentist/id/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registration").value("401036"))
                .andExpect(jsonPath("$.name").value("Augusto"))
                .andExpect(jsonPath("$.lastName").value("Rinaldi"));
    }


    @Test
    @Order(3)
    public void testPostdentist() throws Exception {

        String dentistSaved = "{\"registration\": \"125\", \"name\": \"Juan\", \"lastName\": \"Perez\"}";

        mockMvc.perform(post("/dentist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dentistSaved)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registration").value("125"))
                .andExpect(jsonPath("$.name").value("Juan"))
                .andExpect(jsonPath("$.lastName").value("Perez"));
    }


    @Test
    @Order(1)
    public void testGetAllDentist() throws Exception{
        mockMvc.perform(get("/dentist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }


    @Test
    void save() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}