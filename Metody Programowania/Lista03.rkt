#lang racket
(require rackunit)

;Zadanie 1
;'((car (a . b)) (* 2))
;(list (list 'car (cons 'a 'b)) (list '* 2))
;'(,(car '(a . b)) ,(* 2))
;(list 'a 2)
;'((+ 1 2 3) (cons) (cons a b))
;(list (list '+ 1 2 3) (list 'cons) (list 'cons 'a 'b))

(check-equal? (equal? '((car (a . b)) (* 2)) (list (list 'car (cons 'a 'b)) (list '* 2))) #t)
(check-equal? (equal? `(,(car '(a . b)) ,(* 2)) (list 'a 2)) #t) 
(check-equal? (equal? '((+ 1 2 3) (cons) (cons a b)) (list (list '+ 1 2 3) (list 'cons) (list 'cons 'a 'b))) #t)

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
; ((lambda (x y) (+ x (* x y))) 1 2)
; -> (+ 1 (* 1 2))
; -> (+ 1 2)
; -> 3

; ((lambda (x) x) (lambda (x) x))
; -> (lambda (x) x)
; -> x

; ((lambda (x) (x x) (lambda (x) x))
; -> ((lambda (x) x) (lambda (x) x))
; -> (lambda (x) x)
; -> x

; ((lambda (x) (x x)) (lambda (x) (x x)))
; -> ((lambda (x) (x x)) (lambda (x) (x x)))
; -> ((lambda (x) (x x)) (lambda (x) (x x)))
; -> petla w nieskończoność

;Zadanie 4
;Zlozenie funkcji f i g i przesledenie wykonania 2 wyrazen.
(define (square x) (* x x))

(define (inc x) (+ x 1))

(define (my-compose f g)
  (lambda (x) (f (g x))))

; ((my-compose square inc) 5)
; -> ((lambda (x) (square (inc x))) 5)
; -> (square (inc 5))
; -> (square (+ 5 1))
; -> (square 6)
; -> (* 6 6)
; -> 36

; ((my-compose inc square) 5)
; -> ((lambda (x) (inc (square x))) 5)
; -> (inc (square 5))
; -> (inc (* 5 5))
; -> (inc 25)
; -> (+ 25 1)
; -> 26

;Zadanie 5
;Kilka procedur dla listy.
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
;Utworz predykaty charakterystyczne dla seta.
(define empty-set
  (lambda (x) #f))

(define (singleton a)
  (lambda (x) (equal? a x)))

(define (in a s)
  (s a))

(define (union s t)
  (lambda (x) (or (s x) (t x))))

(define (intersect s t)
  (lambda (x) (and (s x) (t x))))

(check-equal? (empty-set 2) #f)
(check-equal? ((singleton 2) 2) #t)
(check-equal? (in 5 (union (singleton 5) (singleton 6))) #t)
(check-equal? (in 4 (union (singleton 5) (singleton 6))) #f)
(check-equal? ((union (singleton 5) (singleton 6)) 6) #t)
(check-equal? ((union (singleton 5) (singleton 6)) 4) #f)
(check-equal? ((intersect (union (singleton 5) (singleton 6)) (union (singleton 4) (singleton 5))) 5) #t)
(check-equal? ((intersect (union (singleton 5) (singleton 6)) (union (singleton 4) (singleton 5))) 6) #f)

;Zadanie 7
;Jak duzo consow tworzy ponizsza procedura dlugosci n?
(define (foldr-reverse xs)
  (foldr (lambda (y ys) (append ys (list y))) null xs))

;(length (foldr-reverse (build-list 10000 identity)))

;Append tworzy nową listę zawierającą wszystkie elementy swoich argumentów,
;wiec dla każdego elementu wejsciowego musi zostać utworzona nowa lista jednoelementowa.
;W sumie utworzonych zostanie n list jednoelementowych
;oraz dla każdej listy jednoelementowej musi zostać utworzona nowa lista,
;która łączy listę jednoelementową z pozostałą częścią odwróconej listy.
;Wiec append tworzy listę, która jest długości 2,
;następnie lista łączy się z następną listą jednoelementową,
;tworząc listę długości 3 i tak dalej.
;Oznacza to, że dla każdej z n jednoelementowych list wykonamy dodatkowe (n-1) kroków łączenia list.
;Finalnie procedura towrzy n-1 + n-2 + ... + 2 + 1 = n(n-)/2 consow.
;Ale potrzeba tylko n, wiec reszta to nieuzytki

;Zadanie 8
(define (list->llist xs)
  (lambda (x) (append xs x)))

(define (llist->list f)
  (f null))

(define (llist-singleton x)
  (lambda (y) (append (list x) y)))

(define llist-null
  (lambda (x) append null x))

(define (llist-append f g)
  (lambda (x) (f (g x))))

(define (foldr-llist-reverse xs)
  (llist->list (foldr (lambda (x f) (llist-append f (llist-singleton x))) llist-null xs)))







