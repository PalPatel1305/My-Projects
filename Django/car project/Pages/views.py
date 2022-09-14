from django.shortcuts import render
from django.http import HttpResponse
from .models import Team
# Create your views here.
def home(request):
    team_data=Team.objects.all()
    context={
        "TD":team_data
    }
    return render(request,"Pages/home.html",context)


def about(request):
    team_data=Team.objects.all()
    context={
        "TD":team_data
    }
    return render(request,"Pages/about.html",context)


def contact(request):
    return render(request,"Pages/contact.html")


def services(request):
    return render(request,"Pages/services.html")


# Create your views here.
