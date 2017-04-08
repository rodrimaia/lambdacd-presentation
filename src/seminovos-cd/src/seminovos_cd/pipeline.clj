(ns seminovos-cd.pipeline
  (:use [lambdacd.steps.control-flow]
        [seminovos-cd.steps])
  (:require
   [lambdacd.steps.manualtrigger :as manualtrigger]
   ))

(def pipeline-def
  `(
    (alias "Initialize"
           (either
            manualtrigger/wait-for-manual-trigger
            wait-for-repo))
    (alias "Build and Test"
           (with-repo
             build-image
             run-tests
             ))
    (alias "Deploy"
           (with-repo
             deploy))))
