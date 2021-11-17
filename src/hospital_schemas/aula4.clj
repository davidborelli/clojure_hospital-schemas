(ns hospital_schemas.aula4
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

(pprint (s/validate Paciente {:id 15, :nome "David", :plano [:raio-x, :ultrasom]}))
(pprint (s/validate Paciente {:id 15, :nome "David", :plano [:raio-x]}))
(pprint (s/validate Paciente {:id 15, :nome "David", :plano []}))
(pprint (s/validate Paciente {:id 15, :nome "David", :plano [], :nascimento "27/09/1989"}))

; Outro tipo de uso de mapas
; Pacientes { 15 {PACIENTE}, 32 {PACIENTE} }
(def Pacientes
  {PosInt Paciente})

(pprint (s/validate Pacientes {}))
(let [david {:id 15, :nome "David", :plano [:raio-x]}
      erick {:id 20, :nome "Erick", :plano []}]
  (pprint (s/validate Pacientes {15 david}))
  (pprint (s/validate Pacientes {15 david, 20 erick}))
  ;(pprint (s/validate Pacientes {-15 david}))
  ;(pprint (s/validate Pacientes {15 15}))
  ;(pprint (s/validate Pacientes {15 {:id 15, :nome "David"}}))
  )