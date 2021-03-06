(ns hospital_schemas.aula5
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PosInt (s/pred pos-int? 'inteiro-positivo))
(def Plano [s/Keyword])
(def Paciente
  {:id                          PosInt,
   :nome                        s/Str,
   :plano                       Plano,
   (s/optional-key :nascimento) s/Str})

(def Pacientes
  {PosInt Paciente})

(def Visitas
  {PosInt [s/Str]})


; Garantia que existe um ID válido
; (pprint (s/validate Paciente { :id nil, :nome "David", :plano [] }))

; Foi removido o if-def, também o throw pois
; o schema GARANTIU a existência do ID e a VALIDADE do id
; ... se a validação estiver ativa
(s/defn adiciona-paciente :- Pacientes
  [pacientes :- Pacientes, paciente :- Paciente]
  (let [id (:id paciente)]
    (assoc pacientes id paciente)))

(s/defn adiciona-visitas :- Visitas
  [visitas :- Visitas, paciente :- PosInt, novas-visitas :- [s/Str]]
  (if (contains? visitas paciente)
    (update visitas paciente concat novas-visitas)
    (assoc visitas paciente novas-visitas)))

(s/defn imprime-relatorio-de-paciente [visitas :- Visitas, paciente :- PosInt]
  (println "Visitas do paciente" paciente "são" (get visitas paciente)))

(defn testa-uso-de-pacientes []
  (let [guilherme {:id 15, :nome "Guilherme", :plano []}
        daniela {:id 20, :nome "Daniela", :plano []}
        paulo {:id 25, :nome "Paulo", :plano []}

        ; uma variação com reduce, mais natural
        pacientes (reduce adiciona-paciente {} [guilherme, daniela, paulo])

        ; uma variação com shadowing, não é a melhor prática
        visitas {}
        visitas (adiciona-visitas visitas 15 ["01/01/2019"])
        visitas (adiciona-visitas visitas 20 ["01/02/2019", "01/01/2020"])
        visitas (adiciona-visitas visitas 15 ["01/03/2019"])]
    (pprint pacientes)
    (pprint visitas)

    (imprime-relatorio-de-paciente visitas 20)))

(testa-uso-de-pacientes)