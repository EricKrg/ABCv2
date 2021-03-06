import csv
import sys
import os
import plotly.graph_objs as go
from plotly.offline import plot

# simply visual. script for the final logfile using plotly to create a html

inData = sys.argv[1] if len(sys.argv) > 1 else  'logs/newLog.txt'
print(inData)
time, percip, sim,real = ([] for i in range(4))
with open(inData) as csv_file:
    reader = csv.reader(csv_file, delimiter='\t')
    line = 0
    err = ""
    for row in reader:
        if line == 0:
            line=+1
        else:
            #print(row)
            try:
                time.append(row[0])
                percip.append(row[1])
                sim.append(row[2])
                real.append(row[3])
            except IndexError:
                err = row[0]
                print("end")
# create data traces
# sim
trace = go.Scatter(
    x = time[0:-1],
    y = sim,
    line = dict(
        color = ('rgb(200, 0, 0)'),
        width = 1.5),
    name = 'simulierter Abfluss'
)

# real
trace2 = go.Scatter(
    x = time[0:-1],
    y = real,
    line = dict(
        width = 1.5),
    name = 'gemessener Abfluss'
)
# percip
trace3 = go.Scatter(
    x = time[0:-1],
    y = percip,
    line = dict(
        color = ('rgb(0, 0, 100)'),
        width = 0.5,
        dash = 'dot'),
    name = 'Niederschlag'
)

# annotation stuff
# Source

# layout stuff
layout = dict(title = '<b>Simulierter und gemessener Abfluss {}</b><br>{}'.format(os.path.basename(inData),err),
              xaxis = dict(title = '<b>Zeit</b>'),
              yaxis = dict(title = '<b>Abfluss(mm/s) /Niederschlag(mm)</b>'),
              )
data = [trace3,trace,trace2]

fig = dict(data=data, layout=layout)

plot(fig, filename='index.html')


