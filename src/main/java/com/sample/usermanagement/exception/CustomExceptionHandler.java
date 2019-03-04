package com.sample.usermanagement.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sample.usermanagement.constant.Constant;
import com.sample.usermanagement.error.ErrorResponse;



/** Custom ExceptionHandler : centralized exception handling across all @RequestMapping 
 * @author nitin
 *
 */

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
    
    /** This Method is for handling all generic Exception
     * @param ex
     * @param request
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(Constant.ERROR_SERVICE, details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
    /** Handles Specific RecordNotFoundException
     * @param ex
     * @param request
     * @return ResponseEntity
     */
    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(Constant.ERROR_RECORD_NOT_FOUND, details);
        return new ResponseEntity(error, HttpStatus.NO_CONTENT);
    }
 
    /** Handles request validation errors in @RequestBody 
     * @param ex
     * @param request
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse(Constant.ERROR_VALIDATION_FAILED, details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}