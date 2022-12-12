import numpy as np
import matplotlib.pyplot as plt
from results import *

jump = 100
servant = False
fig_number = '2.2.3'


fig, ax = plt.subplots()
ax.set_title('Wykres ' + fig_number + ' Proporcja synch. do asynch. pracy klienta')

x = np.linspace(10, 100, 10)
y = np.linspace(10, 100, 10)
if jump == 100:
    x *= 10
    y *= 10

z1 = get_data(False, jump, servant)
z2 = get_data(True, jump, servant)
z = z2 / z1

im = ax.imshow(z)

fig.colorbar(im, shrink=1, anchor=(0, 0.5))

# Show all ticks and label them with the respective list entries
ax.set_xticks(np.arange(len(x)), labels=x)
ax.set_yticks(np.arange(len(y)), labels=y)

# Rotate the tick labels and set their alignment.
plt.setp(ax.get_xticklabels(), rotation=45, ha="right",
         rotation_mode="anchor")

# Loop over data dimensions and create text annotations.
# for i in range(len(y)):
#     for j in range(len(x)):
#         text = ax.text(j, i, z[i, j],
#                        ha="center", va="center", color="w")

ax.set_xlabel('Sen w servant\'cie [ms]')
ax.set_ylabel('Sen w kliencie [ms]')
fig.tight_layout()
plt.show()
