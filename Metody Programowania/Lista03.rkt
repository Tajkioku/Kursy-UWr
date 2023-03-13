#lang racket
(require rackunit)

;Zadanie 1
;Zapisz wyrazenia bez cytowania
'((car (a . b)) (* 2))
(cons '(car (a . b)) '((* 2)))

'(,(car '(a . b)) ,(* 2))
(cons ' ,(car '(a . b)) '(,(* 2)))

'((+ 1 2 3) (cons) (cons a b))
(list '(+ 1 2 3) '(cons) '(cons a b))

;Zadanie 2
;Iloczyn elementow listy wykorzystujac foldl
(define (my-foldl f x xs)
  (define (it xs acc)
    (if (null? xs)
        acc
        (it (cdr xs) (f (car xs) acc))))
  (it xs x))

(define (my-product xs)
  (if (null? xs)
        0
        (my-foldl * 1 xs)))

(check equal? (my-product (list)) 0)
(check equal? (my-product (list 1 2 3 4)) 24)
(check equal? (my-product (list 1 2 3 0 4)) 0)
(check equal? (my-product (list 1 -2 3 4)) -24)
(check equal? (my-product (list 1 2 -3 -4)) 24)

;Zadanie 3
;Używając modelu podstawieniowego, prześledź wykonanie wyrażeń.

((lambda (x y) (+ x (* x y))) 1 2)
;Wywoła funkcję lambda z argumentami 1 i 2. Wynik to 1 + (1*2) = 3.

((lambda (x) x) (lambda (x) x))
;Wywoła funkcję lambda z argumentem (lambda (x) x), co spowoduje zwrócenie jej samej.

((lambda (x) (x x)) (lambda (x) x))
;Wywołuje funkcję lambda z argumentem (lambda (x) x).
;W wyniku wywołania, ciało funkcji (x x) jest wywołane z samą funkcją lambda (lambda (x) x) jako argumentem.
;W ten sposób wywołanie ciała funkcji (x x) zwraca samo wyrażenie (lambda (x) x).

;((lambda (x) (x x)) (lambda (x) (x x)))
;W wyniku tego wywołania, ciało funkcji (x x) wywołane z funkcją (lambda (x) (x x)) jako argumentem,
;ponownie zwraca samo wyrażenie (lambda (x) (x x)),
;Skutkuje to kolejnym wywołaniem (x x) z tym samym argumentem, w nieskończoną pętlę.
;Wynik to brak wartosci.

;Zadanie 4
;Zlozenie funkcji f i g i przesledenie wykonania wyrazen:
(define (square x) (* x x))

(define (inc x) (+ x 1))

(define (my-compose f g)
  (lambda (x) (f (g x))))

((my-compose square inc) 5)
;(square (inc 5))
;(square 6)
;36

((my-compose inc square) 5)
;(inc (square 5))
;(inc 25)
;26

;Zadanie 5
(define (negatives n)
  (build-list n (lambda (i) (- (+ i 1)))))

(define (reciprocals n)
  (build-list n (lambda (i) (/ 1 (+ i 1)))))

(define (evens n)
  (build-list n (lambda (i) (* i 2))))

(define (identityM n)
  (build-list n
              (lambda (i)
                (build-list n
                            (lambda (j)
                              (if (= i j) 1 0))))))

(check equal? (negatives 0) null)
(check equal? (negatives 5) (list -1 -2 -3 -4 -5))
(check equal? (reciprocals 0) null)
(check equal? (reciprocals 5) (list 1 1/2 1/3 1/4 1/5))
(check equal? (evens 0) null)
(check equal? (evens 5) (list 0 2 4 6 8))
(check equal? (identityM 0) null)
(check equal? (identityM 1) (list (list 1)))
(check equal? (identityM 2) (list (list 1 0) (list 0 1)))
(check equal? (identityM 3) (list (list 1 0 0) (list 0 1 0) (list 0 0 1)))

;Zadanie 6
;Predykaty charakterystyczne dla seta
(define (empty-set? s)
  #f)

(define (singleton? s a)
  (and (= (set-count s) 1)
       (equal? (set-first s) a)))

(define (in? a s)
  (set-member? a s))

(define (union s t)
  (set-union s t))

(define (intersect s t)
  (set-intersect s t))







