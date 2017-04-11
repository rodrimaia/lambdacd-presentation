(ns seminovos-cd.steps
  (:require [lambdacd.steps.shell :as shell]
            [lambdacd.steps.git :as git]))

(def upstream-repo "https://github.com/rodrigomaia17/seminovos-bh-crawler")

(defn wait-for-repo [_ ctx]
  (git/wait-for-git ctx upstream-repo "master"))

(defn with-repo [& steps]
  (git/with-git upstream-repo steps))

(defn build-image [args ctx]
  (shell/bash ctx (:cwd args) "docker build . -t seminovos" ))

(defn run-tests [args ctx]
  (shell/bash ctx (:cwd args) "docker run --rm seminovos npm run test" ))

(defn stop-container [args ctx]
  (shell/bash ctx (:cwd args) "docker rm $(docker stop $(docker ps -a -q --filter ancestor=seminovos --format='{{.ID}}')) || true" ))

(defn start-container [args ctx]
  (shell/bash ctx (:cwd args) "docker run -d -p 3000:3000 seminovos:latest npm run crawlerStart"))

