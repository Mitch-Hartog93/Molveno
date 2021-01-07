package com.molvenolakeresort.hotel.controller;

public class EntityNotFoundException extends Exception {
	EntityNotFoundException(String error) {
		super(error);
	}
}
