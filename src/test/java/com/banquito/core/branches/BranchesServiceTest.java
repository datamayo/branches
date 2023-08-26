package com.banquito.core.branches;

import com.banquito.core.branches.exception.CRUDException;
import com.banquito.core.branches.model.Branch;
import com.banquito.core.branches.repository.BranchRepository;
import com.banquito.core.branches.service.BranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BranchesServiceTest {
    private Branch branch = new Branch();
    @InjectMocks
    private BranchService branchService;

    @Mock
    private BranchRepository branchRepository;

    @BeforeEach
    void setUp() {
    this.branchService = new BranchService(this.branchRepository);

        this.branch.setId("123");
        this.branch.setName("Branch1");
        this.branch.setCode("12345");
    }

    @Test
    void testLookById(){
        when(this.branchRepository.findById("123")).thenReturn(Optional.of(this.branch));
        assertDoesNotThrow(() -> {
            this.branchService.lookById("123");
        });
        assertThrows(CRUDException.class, () -> {
            this.branchService.lookById("1234");
        });
    }

    @Test
    void testLookByCode(){
        when(this.branchRepository.findByCode("12345")).thenReturn(this.branch);
        assertDoesNotThrow(() -> {
            this.branchService.lookByCode("12345");
        });
    }

    @Test
    void testGetAll(){
        List<Branch> branches = new ArrayList<>();
        branches.add(this.branch);
        when(this.branchRepository.findAll()).thenReturn(branches);
        assertDoesNotThrow(() -> {
            this.branchService.getAll();
        });
    }

   @Test
    void testCreate(){
       Branch branchCreate = new Branch();
       branchCreate.setCode("12345");
       branchCreate.setName("Branch1");
       branchCreate.setId("123");
        when(this.branchRepository.save(branchCreate)).thenReturn(this.branch);
       assertDoesNotThrow(() -> {
           this.branchService.create(branchCreate);
       });
    }

    @Test
    void testUpdate(){
        Branch branchCreate = new Branch();
        branchCreate.setCode("12345");
        branchCreate.setName("Branch2");
        branchCreate.setId("modificado123");
        when(this.branchRepository.findByCode("12345")).thenReturn(this.branch);
        assertDoesNotThrow(() -> {
            this.branchService.update("12345", branchCreate);
        });
        assertThrows(CRUDException.class, () -> {
            this.branchService.update("1234", branchCreate);
        });
    }

}
