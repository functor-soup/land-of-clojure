(require '[clojure.string :as str])
(load-file  "../chapter5/textgameengine.clj")

(def *allowed-commands* '(look walk pickup inventory))

(defn game-read
  []
  (let [cmd (read-line)
        command-string (read-string (str "(" cmd  ")"))
        game-command (first command-string)
        arguments (rest command-string)
        ]
      (cons game-command (map str arguments))))

(defn game-eval [sexp]
  (if (some #(= (first sexp) %) *allowed-commands*)
    (eval sexp)
    "I do not know that command"))

(defn game-print [string]
  (println " -:-> " string))


;; todo-arity checker
(defn game-repl []
  (let [cmd (game-read)]
    (when-not (= (first cmd) 'quit)
            (-> cmd
                (game-eval)
                (game-print))
                (recur))))

 


