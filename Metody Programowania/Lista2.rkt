#lang racket
(require rackunit)

;Zadanie 1
;(let ([x 3])
;  (+ x y))
;x-związane, y-wolne

;(let ([x 1]
;      [y (+ x 2)])
;  (+ x y))
;1.x-związane, 2.x-wolne, y-wolne

;(let ([x 1])
;  (let ([y (+ x 2)])
;    (* x y)))
;Kazda zwiazana


;(define (f x y)
;  (* x y z))
;f,x,y-związane z-wolne

;(define (f x)
;  (define (g y z)
;    (* x y z))
;  (f x x  x))
;Kazda zwiazana

;Zadanie 2
(define fib-0 0)
(define fib-1 1)

(define (fib n)
  (cond
    [(= n 0) fib-0]
    [(= n 1) fib-1]
    [else (+ (fib (- n 1)) (fib (- n 2)))]))

(define (fib-iter n)
  (define (loop cnt a b)
    (if (= n cnt)
        a
        (loop (+ cnt 1) b (+ a b))))
  (loop 0 fib-0 fib-1))

#|
(define (fib-iter2 n)
  (let loop ((cnt 0) (a fib-0) (b fib-1))
    (if (= n cnt)
        a
        (loop (+ cnt 1) b (+ a b)))))
|#

(check equal? (fib 2) 1)
(check equal? (fib 3) 2)
(check equal? (fib 4) 3)
(check equal? (fib 5) 5)
(check equal? (fib 6) 8)
(check equal? (fib-iter 2) 1)
(check equal? (fib-iter 3) 2)
(check equal? (fib-iter 4) 3)
(check equal? (fib-iter 5) 5)
(check equal? (fib-iter 6) 8)

;Zadanie 3 i 4
(define-struct matrix (a b c d) #:transparent)

(define (matrix-mult m n)
  (let ((a (matrix-a m)) (b (matrix-b m)) (c (matrix-c m)) (d (matrix-d m))
        (e (matrix-a n)) (f (matrix-b n)) (g (matrix-c n)) (h (matrix-d n)))
    (make-matrix (+ (* a e) (* b g)) (+ (* a f) (* b h))
                 (+ (* c e) (* d g)) (+ (* c f) (* d h)))))

(define matrix-id (make-matrix 1 0 0 1))

(define (matrix-expt m k)
  (cond ((= k 0) matrix-id)
        ((= k 1) m)
        ((even? k) (matrix-expt (matrix-mult m m) (/ k 2)))
        (else (matrix-mult m (matrix-expt m (- k 1))))))

(define (fib-matrix k)
  (let ((fib-matrix (make-matrix 1 1 1 0)))
    (matrix-b (matrix-expt fib-matrix k))))


(check equal? (matrix-mult (matrix 2 1 -1 4) (matrix 1 3 -2 0)) (matrix 0 6 -9 -3))
(check equal? (matrix-expt (matrix 2 2 2 2) 3) (matrix 32 32 32 32))
(check equal? (fib-matrix 2) 1)
(check equal? (fib-matrix 3) 2)
(check equal? (fib-matrix 4) 3)
(check equal? (fib-matrix 5) 5)
(check equal? (fib-matrix 6) 8)

;Zadanie 5
(define (elem? x xs)
  (cond ((null? xs) #f)
        ((equal? x (car xs)) #t)
        (else (elem? x (cdr xs)))))


(check equal? (elem? 2 (list 1 2 3)) #t)
(check equal? (elem? 4 (list 1 2 3)) #f)

;Zadanie 6
(define (maximum xs)
  (if (null? xs)
      -inf.0
      (let loop ((xs xs) (max -inf.0))
        (if (null? xs)
            max
            (let ((x (car xs)))
              (if (> x max)
                  (loop (cdr xs) x)
                  (loop (cdr xs) max)))))))


(check equal? (maximum (list 1 5 0 7 1 4 1 0)) 7)
(check equal? (maximum (list)) -inf.0)

;Zadanie 7
(define (sufixes xs)
  (if (null? xs)
      (list '())
      (cons xs (sufixes(cdr xs)))))


(sufixes (list 1 2 3 4))
(sufixes (list 1 5 0 7 1 4 1 0))

;Zadanie 8
(define (sorted xs)
  (define (it x xs)
    (if (null? xs)
        #t
        (if (> x (car xs))
            #f
            (it (car xs) (cdr xs)))))
  (it (car xs) (cdr xs)))


(check equal? (sorted (list 1 2 3 4 4)) #t)
(check equal? (sorted (list 1 2 5 4)) #f)

;Zadanie 9
(define (select xs)
  (define (it x ys)
    (if (null? ys)
        (cons x (remove x xs))
        (if (< (car ys) x)
            (it (car ys) (cdr ys))
            (it x (cdr ys)))))
  (it (car xs) (cdr xs)))

(define (select-sort xs)
  (if (null? xs)
      null
      (cons (car (select xs)) (select-sort (cdr (select xs))))))

(select (list 4 3 1 2 5))
(select-sort (list 1 5 0 7 1 4 1 0))

;Zadanie 10
(define (split xs)
  (define (it x xs)
    (if (= x 0)
        (list xs)
        (cons (car xs) (it (- x 1) (cdr xs)))))
  (reverse (it (quotient (length xs) 2) xs)))

(define (merge xs ys)
  (if (null? xs)
      ys
      (if (null? ys)
          xs
          (if (< (car xs) (car ys))
              (cons (car xs) (merge (cdr xs) ys))
              (cons (car ys) (merge xs (cdr ys)))))))

(define (merge-sort xs)
  (if (= (length xs) 1)
      xs
      (merge (merge-sort (car (split xs))) (merge-sort (cdr (split xs))))))
  
(split (list 8 2 4 7 4 2 1))
(merge (list 1 4 4 8) (list 2 2 7))
(merge-sort (list 8 2 4 7 4 2 1))
(merge-sort (list 1 5 0 7 1 4 1 0))