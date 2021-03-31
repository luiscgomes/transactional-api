package com.transactions.transactionalapi.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @GetMapping("/{accountId}")
    public ResponseEntity<?> oneById(@PathVariable Long accountId) {
        return ResponseEntity.ok("ola");
    }

}
