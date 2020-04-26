'''
A demonstration of a quantum coin toss challenge on the IBM Q
written by Marcus Edwards on November 20, 2018
'''

from qiskit import IBMQ, QuantumCircuit, execute, Aer
from qiskit.providers.ibmq import least_busy
import csv
import random
import time

#  program time tracking
startTime = time.time()


API_TOKEN = '7f744419bf02c5ad2935c5bad2d858f150f3793da866268cd5c0841bc531c8a72a4484d4c59bf3afa44c29e7859a875cf0634104707aa341424b662acd7c4655'
print('\n-----------------------------------------------------')
IBMQ.save_account(API_TOKEN, overwrite=False)
IBMQ.load_account()
# each provider has multiple backends
provider = IBMQ.get_provider(hub='ibm-q', group='open', project='main')

print('\nThese are the backends that are real quantum devices and are currently operational....')
print(provider.backends(simulator=False, operational=True), '\n')
print('\nLets pick the least busy backend....')
backend = least_busy(provider.backends(simulator=False, operational=True))
print('Backend being used:', backend, ', which has the following available basis gates:')
print(backend.configuration().basis_gates, '\n')

# Quantum circuit configuration
heads_circuit = QuantumCircuit(1,1)
tails_circuit = QuantumCircuit(1,1)
#  heads circuit config
heads_circuit.h(0)
heads_circuit.h(0)
heads_circuit.measure(range(1), range(1))
#  tails circuit config
tails_circuit.h(0)
tails_circuit.x(0)
tails_circuit.h(0)
tails_circuit.measure(range(1), range(1))
#  decompose our original quantum circuits to quantum computer backend's basis
heads_basis = heads_circuit.decompose()
tails_basis = tails_circuit.decompose()

def random_toss():
    return (random.uniform(0, 1) > 0.5)

def run_challenge(trials, flipped, device):
    '''
    Coin toss challenge on the quantum computer.

    Open QASM:
    
        IBMQASM 2.0;
        include "qelib1.inc";
        qreg q[1]; //define 1 quibit register
        creg c[1]; //define 1 classical register
        h q[0]; //perfrom hadamard on q[0]
        x q[0]; //perform NOT on q[0] (if random toss results in 1)
        h q[0]; //perfrom hadamard on q[0] again
        measure q[0] -> c[0]; //measure q[0] into c0[0]
    '''
    heads = 0
    tails = 0

    
    for trial in range(trials):
        if (flipped and random_toss() == True):
            tails += 1
        else:
            heads += 1

    if heads>0:
        heads_job_results = execute(heads_basis, backend, shots=heads).result()
        heads_job = heads_job_results.get_counts(heads_circuit)
        print('\nHeads Job results:\n')
        print( heads_job_results, '\n')
    else:
        heads_job = 0

    if tails>0:
        tails_job_results = execute(tails_basis, backend, shots=tails).result()
        tails_job = tails_job_results.get_counts(tails_circuit)
        print('\nTails Job results:\n')
        print(tails_job_results, '\n')
    else:
        tails_job = 0
            
    # exp_heads = api.execute(heads_qasm, device, heads)
    # exp_tails = api.execute(tails_qasm, device, tails)

    return heads_job, tails_job, heads, tails

def get_choices(file):
    flip = 0
    dont_flip = 0
    with open(file) as csvfile:
        filereader = csv.reader(csvfile)
        for row in filereader:
            if 'Circle' in row[0]:
                flip += 1
            elif 'Square' in row[0]:
                dont_flip += 1
    return flip, dont_flip

flips, passes = get_choices('flip_choices.csv')

print('{0} people are playing a game against IBMs quantum computer.'.format(flips + passes))
print('{0} people chose to flip.'.format(flips))

exp_heads, exp_tails, heads, tails = run_challenge(flips, flipped=True, device='ibmqx4') #run protocol for each flip
print(exp_heads, '\n', exp_tails)

p_heads = heads/(heads+tails)
p_tails = tails/(heads+tails)

print('{0} people who flipped got heads.'.format(heads))
print('{0} people who flipped got tails.'.format(tails))

print('\nPeople who flipped got the following results:')
print("state         Probability")
print("heads         {0}".format(p_heads))
print("tails         {0}".format(p_tails))
                  
print('End result after the quantum computer flips after the human has flipped:')
print("state         Probability")
print("heads         {0}".format((exp_heads['0']+exp_tails['0'])/flips))
print("tails         {0}".format((flips - (exp_heads['0']+exp_tails['0'])) / flips))

print('\n{0} people chose to pass.'.format(passes))

exp_heads, exp_tails, heads, tails = run_challenge(passes, flipped=False, device='ibmqx4') #run protocol for each pass
print(exp_heads, '\n', exp_tails)

print('End result after the quantum computer flips after the human passes:')
print("state         Probability")
print("heads         {0}".format(exp_heads['0']/passes))
print("tails         {0}".format((passes-exp_heads['0'])/passes))

print('Program time is %.2f'.format(time.time()-startTime))

