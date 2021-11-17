(ns hospital_schemas.aula3
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PosInt (s/pred pos-int? 'inteiro-positivo))

(def Paciente
  {:id PosInt :nome s/Str})

(s/defn novo-paciente :- Paciente
  [id :- PosInt
   nome :- s/Str]
  {:id id, :nome nome})

(pprint (novo-paciente 15 "David"))
;(pprint (novo-paciente -5 "David"))

(pprint "-----------------------------------------------------")

(defn maior-igual-zero?
  [valor]
  (>= valor 0))
(def ValorFinanceiro (s/constrained s/Num maior-igual-zero?))

(def Pedido
  {:paciente     Paciente
   :valor        ValorFinanceiro
   :procedimento s/Keyword})

; será que faz sentido "mini-schemas" como aliases ?
; (def Procimento s/Keyword)

(s/defn novo-pedido :- Pedido
  [paciente :- Paciente,
   valor :- ValorFinanceiro,
   procedimento :- s/Keyword]
  {:paciente paciente, :valor valor, :procedimento procedimento})

(pprint (novo-pedido (novo-paciente 15, "David Borelli"), 15.33, :raio-x))
;(pprint (novo-pedido (novo-paciente 15, "David Borelli"), -15.33, :raio-x))










