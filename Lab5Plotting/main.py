import matplotlib.pyplot as plt


x = ['1/5', '2/5', '3/5', '4/5', '5/5']
four_cond_averages = [869314, 725941, 634012, 559830, 513353]
nested_locks_averages = [2686432, 2657780, 2648108, 2595231, 2549048]

fig, ax = plt.subplots()
ax.plot(four_cond_averages, color='green', label='4 cond')
ax.plot(nested_locks_averages, color='red', label='nested locks')
ax.legend(loc='center right')
default_x_ticks = range(len(x))
plt.xticks(default_x_ticks, x)
plt.xlabel('Producers to consumers ratio')
plt.ylabel('Consumption average')

plt.show()