'''
A demonstration of a quantum coin toss challenge on the IBM Q
written by Marcus Edwards on November 20, 2018
'''

from IBMQuantumExperience import IBMQuantumExperience 
import csv
import random

API_TOKEN = 'a0f9090f4b9b0a7f86cb31848730654bb4dbc35aab364a7d728162c96b264752d413b88daea7303c87f12e0a719345119c0f8a880a27d73b998887664a989fce'
heads_qasm = "IBMQASM 2.0;\ninclude \"qelib1.inc\";\nqreg q[1];\ncreg c[1];\nh q[0];\nx q[0];\nh q[0];\nmeasure q[0] -> c[0];\n"
tails_qasm = "IBMQASM 2.0;\ninclude \"qelib1.inc\";\nqreg q[1];\ncreg c[1];\nh q[0];\nh q[0];\nmeasure q[0] -> c[0];\n"

def test_api_auth_token():
    '''
    Authentication with Quantum Experience Platform
    '''
    api = IBMQuantumExperience(API_TOKEN)
    credential = api.check_credentials()

    return credential

def connect():
    '''
    Attempt to connect to the Quantum Experience Platform
    ''' 
    connection_success = test_api_auth_token()

    if(connection_success == True):
        print("API auth success.")
    else:
        print("API auth failure.")
        exit()

def print_results(exp):
    '''
    Print the distribution of measured results from the given experiment
    '''
    print("state     probability")
    for i in range(len(exp['result']['measure']['labels'])):
        if exp['result']['measure']['labels'][i] == '0':
            label = 'heads'
        else:
            label = 'tails'
        print("{0}         {1}".format(label ,exp['result']['measure']['values'][i]))    

    return

def combine_results(heads_exp, tails_exp, head_bias, tail_bias):
    '''
    Combine the distributions of measured results from the given experiments
    '''
    results = {}
    for i in range(len(heads_exp['result']['measure']['labels'])):
        state = heads_exp['result']['measure']['labels'][i]
        if (state == '0'):
            bias = head_bias 
        else: 
            bias = tail_bias
        if state in results:
            results[state] += heads_exp['result']['measure']['values'][i] * bias
        else:
            results[state] = heads_exp['result']['measure']['values'][i] * bias
            
    for j in range(len(tails_exp['result']['measure']['labels'])):
        state = tails_exp['result']['measure']['labels'][i]
        if (state == '0'):
            bias = head_bias 
        else: 
            bias = tail_bias
        if state in results:
            results[state] += tails_exp['result']['measure']['values'][i] * bias
        else:
            results[state] = tails_exp['result']['measure']['values'][i] * bias

    return results

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
    api = IBMQuantumExperience(API_TOKEN)
    
    for trial in range(trials):
        if (flipped and random_toss() == True):
            tails += 1
        else:
            heads += 1
            
    exp_heads = api.run_experiment(heads_qasm, device, heads)
    exp_tails = api.run_experiment(tails_qasm, device, tails)

    return exp_heads, exp_tails, heads, tails

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
    
connect() #connect to IBM Q

flips, passes = get_choices('flip_choices.csv')

print('{0} people chose to flip.'.format(flips))

exp_heads, exp_tails, heads, tails = run_challenge(flips, flipped=True, device='ibmqx4') #run protocol for each flip

p_heads = heads/(heads+tails)
p_tails = tails/(heads+tails)

print('{0} people who flipped got heads.'.format(heads))
print('{0} people who flipped got tails.'.format(tails))

print('Human coin results:')
print("state     probability")
print("heads         {0}".format(p_heads))
print("tails         {0}".format(p_tails))
                       
print('Quantum coin results:')
results = combine_results(exp_heads, exp_tails, p_heads, p_tails)
print("state     probability")
print("heads         {0}".format(results['0']))
print("tails         {0}".format(results['1']))

print('{0} people chose to pass.'.format(passes))

exp_heads, exp_tails, heads, tails = run_challenge(passes, flipped=False, device='ibmqx4') #run protocol for each pass

p_heads = heads/(heads+tails)
p_tails = tails/(heads+tails)

print('Human coin results:')
print("state     probability")
print("heads         {0}".format(p_heads))
print("tails         {0}".format(p_tails))

print('Quantum coin results:')
print_results(exp_tails)
