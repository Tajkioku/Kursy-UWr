import matplotlib.pyplot as plt
import numpy as np

def rational_bezier_curve(control_points, weights, n=1000):
    """ Compute a rational Bézier curve using given control points and weights. """
    degree = len(control_points) - 1
    t = np.linspace(0, 1, n)
    curve = np.zeros((n, 2))
    for i in range(n):
        p = np.zeros(2)
        w_sum = 0
        for j in range(degree + 1):
            binom = np.math.comb(degree, j)
            b = binom * (t[i] ** j) * ((1 - t[i]) ** (degree - j))
            p += b * weights[j] * np.array(control_points[j])
            w_sum += b * weights[j]
        curve[i] = p / w_sum
    return curve

# Define control points and weights
control_points = [(0,0), (3.5,36), (25,25), (25,1.5), (-5,3), (-5,33), (15,11), (-0.5,35), (19.5,15.5), (7,0), (1.5,10.5)]
weights = [1, 6, 4, 2, 3, 4, 2, 1, 5, 4, 1]
weights2 = []
for i in weights:
    weights2.append(i+5)

# Compute the curve
curve = rational_bezier_curve(control_points, weights)
curve2 = rational_bezier_curve(control_points, weights2)

# Plot the curve
plt.figure(figsize=(10, 6))
plt.plot(curve[:, 0], curve[:, 1], label='oryginalna')
plt.plot(curve2[:, 0], curve2[:, 1], label='2')
plt.title("Rational Bézier Curve")
plt.xlabel("X")
plt.ylabel("Y")
plt.legend()
plt.grid(True)
plt.show()
