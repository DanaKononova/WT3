<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<tags:master pageTitle="Phohe Details">
    <div class="container">
        <div id="statusMessage" hidden>
            <div id="statusMessageHead" class="panel-heading"></div>
            <div id="statusMessageBody" class="panel-body"></div>
        </div>
    </div>
    <div class="panel"></div>
    <div class="container">
        <h2>${jewelry.model}</h2>
        <div class="row">
            <div class="col-6">
                <img class="rounded" src="https://raw.githubusercontent.com/andrewosipenko/jewelryshop-ext-images/master/${jewelry.imageUrl}">
                <p class="text-justify">${jewelry.description}</p>
                <div class="float-right">
                    <p class="text">Price: $${jewelry.price}</p>
                    <input id="quantity${jewelry.id}">
                    <input id="add-to-cart" class="btn btn-outline-dark border-dark" type="button" onclick="addToCart(${jewelry.id});" value="Add to Cart"/>
                    <p class="text-danger" id="statusMessage${jewelry.id}"></p>
                </div>

            </div>

            <div class="col-1"></div>

            <div class="col-4">
                <h3>Display</h3>
                <table class="table table-bordered table-light container-fluid">
                    <tr>
                        <td>Size</td>
                        <td>${jewelry.displaySizeInches}"</td>
                    </tr>
                    <tr>
                        <td>Resolution</td>
                        <td>${jewelry.displayResolution}</td>
                    </tr>
                    <tr>
                        <td>Technology</td>
                        <td>${jewelry.displayTechnology}</td>
                    </tr>
                    <tr>
                        <td>Pixel density</td>
                        <td>${jewelry.pixelDensity}</td>
                    </tr>
                </table>
                <h3>Dimensions & weight</h3>
                <table class="table table-bordered table-light container-fluid">
                    <tr>
                        <td>Length</td>
                        <td>${jewelry.lengthMm} mm</td>
                    </tr>
                    <tr>
                        <td>Width</td>
                        <td>${jewelry.widthMm} mm</td>
                    </tr>
                    <tr>
                        <td>Weight</td>
                        <td>${jewelry.weightGr} g</td>
                    </tr>
                </table>
                <h3>Camera</h3>
                <table class="table table-bordered table-light container-fluid">
                    <tr>
                        <td>Front</td>
                        <td>${jewelry.frontCameraMegapixels}</td>
                    </tr>
                    <tr>
                        <td>Back</td>
                        <td>${jewelry.backCameraMegapixels}</td>
                    </tr>
                </table>
                <h3>Battery</h3>
                <table class="table table-bordered table-light container-fluid">
                    <tr>
                        <td>Talk time</td>
                        <td>${jewelry.talkTimeHours} hours</td>
                    </tr>
                    <tr>
                        <td>Stand by time</td>
                        <td>${jewelry.standByTimeHours} hours</td>
                    </tr>
                    <tr>
                        <td>Battery capacity</td>
                        <td>${jewelry.batteryCapacityMah} mAh</td>
                    </tr>
                </table>
                <h3>Other</h3>
                <table class="table table-bordered table-light container-fluid">
                    <tr>
                        <td class="align-middle">Colors</td>
                        <td>
                            <ul>
                                <c:forEach var="color" items="${jewelry.colors}">
                                    <li>${color.code}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <td>Device type</td>
                        <td>${jewelry.deviceType}</td>
                    </tr>
                    <tr>
                        <td>Bluetooth</td>
                        <td>${jewelry.bluetooth}</td>
                    </tr>
                </table>
            </div>

            <div class="col-1"></div>
        </div>
    </div>
</tags:master>