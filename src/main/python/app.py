from flask import Flask, request

import service
from query import *

app = Flask(__name__)


@app.route('/', methods=['POST', 'GET'])
def hello():
    return 'hello'


@app.route('/exportTable', methods=['POST', 'GET'])
def export_table():
    link = service.exportTable(request.values.get('tableName'))
    return link


@app.route('/analysis7', methods=['POST', 'GET'])
def analysis7():
    return create_tbC2Inew(request.values.get('threshold'))


@app.route('/analysis8', methods=['POST', 'GET'])
def analysis8():
    return create_tbC2I3(request.values.get("x"))


@app.route('/analysis9', methods=['POST', 'GET'])
def get_json_file():
    with open('./static/community.json') as f:
        result = json.load(f)

    return json.dumps({"msg": result})


@app.route('/communityKPIIndicatorInformationQuery', methods=['POST', 'GET'])
def communityKPIIndicatorInformationQuery():
    startTimeStamp = request.values.get('startTimeStamp')
    endTimeStamp = request.values.get('endTimeStamp')
    field = request.values.get('field')
    communityName = request.values.get('communityName')
    return service.communityKPIIndicatorInformationQuery(startTimeStamp, endTimeStamp, field, communityName)


@app.route("/convertJsonToExcel", methods=['POST', 'GET'])
def convert():
    json_data = request.values.get("data")
    return service.convertJsonToExcel(json_data)


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)
