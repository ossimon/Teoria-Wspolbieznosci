import numpy as np
import matplotlib.pyplot as plt




# X, Y = X.ravel(), Y.ravel()
# Z1 = Z1.ravel()
# Z2 = Z2.ravel()

# bottom = np.zeros_like(Z1)
# width = depth = 2

# print(X, Y, bottom, width, depth, Z, sep='\n')

# fig = plt.figure()

# ax1 = fig.add_subplot(121, projection='3d')
# ax2 = fig.add_subplot(122, projection='3d')
# ax = fig.add_subplot(projection='3d')

# ax1.bar3d(X, Y, bottom, width, depth, Z1)
# ax2.bar3d(X, Y, bottom, width, depth, Z2)

# ax.plot_wireframe(X, Y, Z1)
# ax.plot_wireframe(X, Y, Z2, color='orange')

x, y = x.flatten(), y.flatten()
# z1 = z1.flatten()
# z2 = z2.flatten()
Z = z1 - z2
# Z = np.log(z3)
Z = Z.flatten()
# print(np.min(Z), np.max(Z))
sign = np.ones_like(Z)
sign[Z < 0] = -1
Z = np.log(np.abs(Z)) * sign

# ax.plot_trisurf(x, y, Z)
# ax.plot_trisurf(x, y, z2)
# ax.set_zlim(np.min(Z), np.max(Z))

# plt.show()
bowl = np.zeros_like(z1, dtype='uint8')
bowl[z1 > z2] = 255
plt.imshow(bowl)
