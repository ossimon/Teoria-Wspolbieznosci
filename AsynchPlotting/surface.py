import matplotlib.pyplot as plt
from results import *
from matplotlib.ticker import LinearLocator
import numpy as np

synch = False
jump = 100
servant = False
fig_number = '2.2.2'

fig, ax = plt.subplots(subplot_kw={"projection": "3d"})
ax.set_title('Wykres ' + fig_number + '. Asynchroniczna praca klienta')

# 0 is servant buffer change, 1 is client's additional work.

x = np.linspace(10, 100, 10)
y = np.linspace(10, 100, 10)
if jump == 100:
    x *= 10
    y *= 10
x, y = np.meshgrid(x, y)

z = get_data(synch, jump, servant)

# Plot the surface.
surf = ax.plot_surface(x, y, z, cmap='viridis',
                       linewidth=0, antialiased=False)

# Name the axes
ax.set_xlabel('Sen w servant\'cie [ms]')
ax.set_ylabel('Sen w kliencie [ms]')
ax.set_zlabel('Liczba wykonanych prac')

# Customize the z axis.
ax.set_zlim(np.min(z), np.max(z))
ax.zaxis.set_major_locator(LinearLocator(10))

# A StrMethodFormatter is used automatically
ax.zaxis.set_major_formatter('{x:.0f}')

# Add a color bar which maps values to colors.
fig.colorbar(surf, shrink=0.5, anchor=(0.5, 0.5))

plt.show()
