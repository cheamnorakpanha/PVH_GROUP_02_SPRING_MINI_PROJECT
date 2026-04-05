package org.me.pvh_group_02_spring_mini_project.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.me.pvh_group_02_spring_mini_project.exception.GlobalException;
import org.me.pvh_group_02_spring_mini_project.service.HabitService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HabitControllerValidationTest {

    private LocalValidatorFactoryBean validator;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        HabitService habitService = mock(HabitService.class);
        HabitController habitController = new HabitController(habitService);

        mockMvc = MockMvcBuilders.standaloneSetup(habitController)
                .setControllerAdvice(new GlobalException())
                .setValidator(validator)
                .build();
    }

    @AfterEach
    void tearDown() {
        validator.close();
    }

    @Test
    void getAllHabitsShouldReturnBadRequestWhenPageIsZero() throws Exception {
        mockMvc.perform(get("/api/v1/habits").param("page", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.instance").value("/api/v1/habits"))
                .andExpect(jsonPath("$.errors.page").value("must be greater than 0"));
    }
}
