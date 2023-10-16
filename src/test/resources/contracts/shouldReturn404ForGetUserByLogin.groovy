package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/users/testUser7529'
        //body()
        headers {
            contentType('application/json')
        }
    }
    response {
        status NOT_FOUND()
        body([
                "errorMessage": "User with login [testUser7529] does not exists."
        ])
        headers {
            contentType('application/json')
        }
    }
}