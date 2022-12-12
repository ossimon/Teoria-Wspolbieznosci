import matplotlib.pyplot as plt
from results import *
import numpy as np

servant = False
client_zero = True
fig_number = '3.1.2'

fig, ax = plt.subplots()
ax.set_title('Wykres ' + fig_number + ' Praca klienta przy "darmowej" pracy klienta\'a')
ax.set_xlabel('Sen w buforze [ms]')
ax.set_ylabel('Praca klienta')

x = np.linspace(10, 100, 10)
y1 = get_0_data(False, servant, client_zero)
y2 = get_0_data(True, servant, client_zero)
# y1 = np.log(y1)
# y2 = np.log(y2)

asynch, = ax.plot(x, y1, label='Metoda asynchroniczna')
synch, = ax.plot(x, y2, label='Metoda synchroniczna')
ax.legend(handles=[asynch, synch])

ax.grid()

plt.show()
