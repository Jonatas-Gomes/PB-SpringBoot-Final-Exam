package br.com.compass.pb.MsHistory.framework.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorValidResponse {
    String field;
    String Error;
}
