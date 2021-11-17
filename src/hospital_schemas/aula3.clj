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

(pprint "-----------------------------------------------------")

(def Numeros [s/Num])
(pprint (s/validate Numeros [15]))
(pprint (s/validate Numeros [15, 13]))
(pprint (s/validate Numeros [15, 13, 123, 132, 321, 12.23]))
(pprint (s/validate Numeros [0]))
; nil não é número, não faz sentido
; (pprint (s/validate Numeros [nil]))
(pprint (s/validate Numeros []))
(pprint (s/validate Numeros nil))

; nil não é s/Num
;(pprint (s/validate s/Num nil))
; nil é [s/Num]
(pprint (s/validate [s/Num] nil))

(def Plano [s/Keyword])
(pprint (s/validate Plano [:raio-x]))

(def Paciente
  {:id PosInt, :nome s/Str, :plano Plano})

(pprint (s/validate Paciente {:id 15, :nome "David", :plano [:raio-x, :ultrasom]}))
(pprint (s/validate Paciente {:id 15, :nome "David", :plano [:raio-x]}))
(pprint (s/validate Paciente {:id 15, :nome "David", :plano []}))
(pprint (s/validate Paciente {:id 15, :nome "David", :plano nil}))
; plano é uma keyword obrigatória no mapa, mas ela pode ter um valor vazio (nil)
; (pprint (s/validate Paciente {:id 15, :nome "David"}))













