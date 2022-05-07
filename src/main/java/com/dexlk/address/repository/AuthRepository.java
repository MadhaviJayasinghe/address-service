package com.dexlk.address.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.dexlk.address.VO.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
@Slf4j
public class AuthRepository {
    @Autowired
    private DynamoDBMapper mapper;
    @Autowired
    private RestTemplate restTemplate;

    public ValidationResponseTemplateVO validate(String token) {
            ValidationResponseTemplateVO vo = new ValidationResponseTemplateVO();
            String url = "http://localhost:9003/validate";

            TokenRequest tokenRequest = new TokenRequest();

            tokenRequest.setToken(token);
            ValidationResponse validationResponse =
                    restTemplate.postForObject(url, tokenRequest, ValidationResponse.class);
            if (validationResponse != null) {
                log.info("valid {}", validationResponse.getResponse());

                if (validationResponse.getResponse().equals("true")) {

                    vo.setValidationResponse(validationResponse);

                    return vo;
                }
            }
            else {
                log.info("Invalid {}", "validationResponse.getResponse()");

            }
//        } catch (Exception e) {
//            log.error("Error " + e.getMessage());
//        }
        return null;
    }
}
