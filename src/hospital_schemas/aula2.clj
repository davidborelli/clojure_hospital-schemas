(ns hospital_schemas.aula2
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

;(s/defrecord Paciente
;  [id :- Long, nome :- s/Str])

;(map->Paciente {15 "David"}) Baseado em um mapa
;(->Paciente 15 "David") Para criar um Paciente

;(pprint (map->Paciente {25 "David Borelli"}))
;(pprint (map->Paciente {"25" "David Borelli"}))

(def Paciente
  "Schema de um paciente"
  {:id s/Num, :nome s/Str})

(pprint (s/explain Paciente))
(pprint (s/validate Paciente {:id 15, :nome "guilherme"}))

; Typo é pego pelo schema, mas poderiamos argumentas que esse
; tipo de erro seria pego em testes automatizados com uma boa cobertura

;(pprint (s/validate Paciente { :id 15, :name "guilherme" }))

; MAS... entra a questão de QUERER ser forward compatible OU NÃO.
; entender esse trade-off
; sistemas externos não me quebraram ao adicionar novos campos (forward compatible)
; no nosso validate não estamos sendo forward compatible (pode ser interessante quando queremos analisar mudanças)
;(pprint (s/validate Paciente { :id 15, :nome "guilherme", :plano [:raio-x] }))



; Chaves que são keywords em schemas são por padrão OBRIGATÓRIAS
;(pprint (s/validate Paciente { :id 15 }))


; Tipo de retorno com schema
; força a validação na saída da função
;(s/defn novo-paciente :- Paciente
;  [id :- s/Num, nome :- s/Str]
;  {:id id, :nome nome, :plano []})

(s/defn novo-paciente :- Paciente
  [id :- s/Num, nome :- s/Str]
  {:id id, :nome nome})

(pprint (novo-paciente 15 "David Borelli"))



; Função pura simples, fácil de testar
(defn estritamente-positivo? [x]
  (> x 0))
(def EstritamentePositivo (s/pred estritamente-positivo? 'estritamente-positivo))
;(def EstritamentePositivo (s/pred estritamente-positivo?))

(pprint (s/validate EstritamentePositivo 15))
;(pprint (s/validate EstritamentePositivo 0))
;(pprint (s/validate EstritamentePositivo -15))

(def Paciente
  "Schema de um paciente"
  { :id (s/constrained s/Int pos?), :nome s/Str })
; é por isso que é importante conhecer bem a documentação
; já existe pos? e tbm pos-int implementado
; UMA DICA: Sempre olhar documentação

(pprint (s/validate Paciente { :id 15, :nome "Guilherme" }))
;(pprint (s/validate Paciente { :id -15, :nome "Guilherme" }))
;(pprint (s/validate Paciente { :id 0, :nome "Guilherme" }))

(def Paciente
  "Schema de um paciente"
  { :id (s/constrained s/Int #(> % 0) 'inteiro-estritamente-possitivo), :nome s/Str })
; Usar lambida não é tão interessante pois na mensagem de erro, é apresentado um nome
; aleatório de função anônima, é bom evitar, parece uma péssima prática

(pprint (s/validate Paciente { :id 1, :nome "David" }))