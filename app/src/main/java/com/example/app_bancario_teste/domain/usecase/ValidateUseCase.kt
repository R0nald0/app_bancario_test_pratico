package com.example.app_bancario_teste.domain.usecase

class ValidateUseCase {
    fun validate(email:String ,password : String ):Map<String, String>{
        val error = mutableMapOf<String,String>()
        if (!email.contains('@')) {
             error.put(key = "email" ,
                 value = "e-mail inávlido,verfique o campo,email deve conter @"
             )
        }

        if (password.length < 6){
            error.put(key = "password" ,
                value = "Senha precisa ter o mínimo 6 caracteres"
            )
        }
        if (!password.any{ it.isLetter()}){
            error.put(key = "password" ,
                value = "Senha precisa ter o pelomenos 1 uma letra"
            )
        }
        if (!password.any{ it.isDigit()}){
            error.put(key = "password" ,
                value = "Senha precisa ter o pelo menos 1 um número"
            )
        }

        return  error
    }

}