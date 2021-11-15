(ns hospital_schemas.aula1
  (:use clojure.pprint))

(defn adiciona-paciente
  [pacientes, paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui id" {:paciente paciente}))))

; { 15 [], 20 [], 24 [] }
(defn adiciona-visitas
  [visitas, paciente, novas-visitas]
  (if (contains? visitas paciente)
    (update visitas paciente concat novas-visitas)
    (assoc visitas paciente novas-visitas)))

(defn imprime-relatorio-de-paciente [visitas, paciente]
  (println "Visitas do paciente" paciente "são" (get visitas paciente)))

(defn testa-uso-de-pacientes []
  (let [guilherme {:id 15, :nome "Guilherme"}
        daniela {:id 20, :nome "Daniela"}
        paulo {:id 25, :nome "Paulo"}

        ; uma variação com reduce, mais natural
        pacientes (reduce adiciona-paciente {} [guilherme, daniela, paulo])

        ; uma variação com shadowing, não é a melhor prática
        visitas {}
        visitas (adiciona-visitas visitas 15 ["01/01/2019"])
        visitas (adiciona-visitas visitas 20 ["01/02/2019", "01/01/2020"])
        visitas (adiciona-visitas visitas 15 ["01/03/2019"])]
    (pprint pacientes)
    (pprint visitas)

    ; não executa como deveria pois está sendo usado o simbolo paciente
    ; em vários lugares com significado diferente
    (imprime-relatorio-de-paciente visitas guilherme)
    (println (get visitas 20))))

(testa-uso-de-pacientes)
