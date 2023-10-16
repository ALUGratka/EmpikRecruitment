package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/users/octocat'
        //body()
        headers {
            contentType('application/json')
        }
    }
    response {
        status OK()
        body([
                "id": $(anyInteger()),
                "login": $(anyNonEmptyString()),
                "name": "The Octocat",
                "type": $(anyOf('User', 'Admin')),
                "avatarUrl": $(anyHttpsUrl()),
                "createdAt": $(anyDateTime()),
                "calculations": $(anyDouble())
        ])
        headers {
            contentType('application/json')
        }
    }
}