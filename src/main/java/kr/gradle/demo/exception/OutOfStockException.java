package kr.gradle.demo.exception;



public class OutOfStockException extends RuntimeException{

    public OutOfStockException(String message){
        super(message);
    }
}
