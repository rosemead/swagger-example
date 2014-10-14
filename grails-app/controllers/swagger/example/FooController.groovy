package swagger.example

import restapidoc.DocumentedRestfulController

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FooController extends DocumentedRestfulController{


    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    FooController() {
        super(Foo)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Foo.list(params), [status: OK]
    }

    @Transactional
    def save(Foo fooInstance) {
        if (fooInstance == null) {
            render status: NOT_FOUND
            return
        }

        fooInstance.validate()
        if (fooInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        fooInstance.save flush:true
        respond fooInstance, [status: CREATED]
    }

    @Transactional
    def update(Foo fooInstance) {
        if (fooInstance == null) {
            render status: NOT_FOUND
            return
        }

        fooInstance.validate()
        if (fooInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        fooInstance.save flush:true
        respond fooInstance, [status: OK]
    }

    @Transactional
    def delete(Foo fooInstance) {

        if (fooInstance == null) {
            render status: NOT_FOUND
            return
        }

        fooInstance.delete flush:true
        render status: NO_CONTENT
    }
}
