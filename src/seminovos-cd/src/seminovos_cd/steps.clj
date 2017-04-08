(ns seminovos-cd.steps
  (:require [lambdacd.steps.shell :as shell]
            [lambdacd.steps.git :as git]))

(def upstream-repo "https://github.com/rodrigomaia17/seminovos-bh-crawler")

(defn wait-for-repo [_ ctx]
  (git/wait-for-git ctx upstream-repo "master"))

(defn with-repo [& steps]
  (git/with-git upstream-repo steps))

(defn build-image [args ctx]
  (shell/bash ctx (:cwd args) "docker build ." ))

(defn run-tests [args ctx]
  (shell/bash ctx (:cwd args) "docker run --rm seminovos npm run test" ))

(defn coverage [args ctx]
  (shell/bash ctx (:cwd args) "docker run --rm seminovos npm run test-cover" ))
