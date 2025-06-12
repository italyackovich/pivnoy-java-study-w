package ttv.poltoraha.pivka.dao.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDto {
    private String error;
    private List<String> message;
}
