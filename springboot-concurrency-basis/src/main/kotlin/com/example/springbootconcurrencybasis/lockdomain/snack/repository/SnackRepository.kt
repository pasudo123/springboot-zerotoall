package com.example.springbootconcurrencybasis.lockdomain.snack.repository

import com.example.springbootconcurrencybasis.lockdomain.snack.model.Snack
import org.springframework.data.jpa.repository.JpaRepository

interface SnackRepository : JpaRepository<Snack, Long>